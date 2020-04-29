/*******************************************************************************
 * Copyright (c) 2013-2015 Sierra Wireless and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * 
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 * 
 * Contributors:
 *     Sierra Wireless - initial API and implementation
 *******************************************************************************/
package org.eclipse.leshan.server.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.californium.core.network.Endpoint;
import org.eclipse.jetty.servlets.EventSource;
import org.eclipse.jetty.servlets.EventSourceServlet;
import org.eclipse.leshan.core.node.LwM2mNode;
import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.core.response.ObserveResponse;
import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.observation.ObservationListener;
import org.eclipse.leshan.server.queue.PresenceListener;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationListener;
import org.eclipse.leshan.server.registration.RegistrationUpdate;
import org.eclipse.leshan.server.servlet.json.LwM2mNodeSerializer;
import org.eclipse.leshan.server.servlet.json.RegistrationSerializer;
import org.eclipse.leshan.server.servlet.log.CoapMessage;
import org.eclipse.leshan.server.servlet.log.CoapMessageListener;
import org.eclipse.leshan.server.servlet.log.CoapMessageTracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



public class EventServlet extends EventSourceServlet {

    private static final String EVENT_DEREGISTRATION = "DEREGISTRATION";

    private static final String EVENT_UPDATED = "UPDATED";

    private static final String EVENT_REGISTRATION = "REGISTRATION";

    private static final String EVENT_AWAKE = "AWAKE";

    private static final String EVENT_SLEEPING = "SLEEPING";

    private static final String EVENT_NOTIFICATION = "NOTIFICATION";

    private static final String EVENT_COAP_LOG = "COAPLOG";

    private static final String QUERY_PARAM_ENDPOINT = "ep";

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(EventServlet.class);

    private final Gson gson;

    private final CoapMessageTracer coapMessageTracer;

    private Set<LeshanEventSource> eventSources = Collections
            .newSetFromMap(new ConcurrentHashMap<LeshanEventSource, Boolean>());

    private final RegistrationListener registrationListener = new RegistrationListener() {

        @Override
        public void registered(Registration registration, Registration previousReg,
                Collection<Observation> previousObsersations) {
        	System.out.println("EventServlet - RegistrationListener - registered");
            String jReg = EventServlet.this.gson.toJson(registration);
            sendEvent(EVENT_REGISTRATION, jReg, registration.getEndpoint());
        }

        @Override
        public void updated(RegistrationUpdate update, Registration updatedRegistration,
                Registration previousRegistration) {
        	System.out.println("EventServlet - RegistrationListener - updated");

            RegUpdate regUpdate = new RegUpdate();
            regUpdate.registration = updatedRegistration;
            regUpdate.update = update;
            String jReg = EventServlet.this.gson.toJson(regUpdate);
            sendEvent(EVENT_UPDATED, jReg, updatedRegistration.getEndpoint());
        }

        @Override
        public void unregistered(Registration registration, Collection<Observation> observations, boolean expired,
                Registration newReg) {
        	System.out.println("EventServlet - RegistrationListener - unregistered");

            String jReg = EventServlet.this.gson.toJson(registration);
            sendEvent(EVENT_DEREGISTRATION, jReg, registration.getEndpoint());
        }

    };

    public final PresenceListener presenceListener = new PresenceListener() {

        @Override
        public void onSleeping(Registration registration) {
        	System.out.println("EventServlet - PresenceListener - onSleeping");

            String data = new StringBuilder("{\"ep\":\"").append(registration.getEndpoint()).append("\"}").toString();

            sendEvent(EVENT_SLEEPING, data, registration.getEndpoint());
        }

        @Override
        public void onAwake(Registration registration) {
        	System.out.println("EventServlet - PresenceListener - onAwake");
            String data = new StringBuilder("{\"ep\":\"").append(registration.getEndpoint()).append("\"}").toString();
            sendEvent(EVENT_AWAKE, data, registration.getEndpoint());
        }
    };

    private final ObservationListener observationListener = new ObservationListener() {

        @Override
        public void cancelled(Observation observation) {
        	System.out.println("EventServlet - ObservationListener - cancelled");

        }

        @Override
        public void onResponse(Observation observation, Registration registration, ObserveResponse response) {
        	System.out.println("EventServlet - ObservationListener - onResponse");

        	if (LOG.isDebugEnabled()) {
                LOG.debug("Received notification from [{}] containing value [{}]", observation.getPath(),
                        response.getContent().toString());
            }

            if (registration != null) {
                String data = new StringBuilder("{\"ep\":\"").append(registration.getEndpoint()).append("\",\"res\":\"")
                        .append(observation.getPath().toString()).append("\",\"val\":")
                        .append(gson.toJson(response.getContent())).append("}").toString();

                sendEvent(EVENT_NOTIFICATION, data, registration.getEndpoint());
            }
        }

        @Override
        public void onError(Observation observation, Registration registration, Exception error) {
        	System.out.println("EventServlet - ObservationListener - onError");

            if (LOG.isWarnEnabled()) {
                LOG.warn(String.format("Unable to handle notification of [%s:%s]", observation.getRegistrationId(),
                        observation.getPath()), error);
            }
        }

        @Override
        public void newObservation(Observation observation, Registration registration) {
        	System.out.println("EventServlet - ObservationListener - newObservation");

        }
    };

    public EventServlet(LeshanServer server, int securePort) {
    	System.out.println("EventServlet - constructor");

        server.getRegistrationService().addListener(this.registrationListener);
        server.getObservationService().addListener(this.observationListener);
        server.getPresenceService().addListener(this.presenceListener);

        // add an interceptor to each endpoint to trace all CoAP messages
        coapMessageTracer = new CoapMessageTracer(server.getRegistrationService());
        for (Endpoint endpoint : server.coap().getServer().getEndpoints()) {
            endpoint.addInterceptor(coapMessageTracer);
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeHierarchyAdapter(Registration.class,
                new RegistrationSerializer(server.getPresenceService()));
        gsonBuilder.registerTypeHierarchyAdapter(LwM2mNode.class, new LwM2mNodeSerializer());
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        this.gson = gsonBuilder.create();
    }

    private synchronized void sendEvent(String event, String data, String endpoint) {
    	System.out.println("EventServlet - sendEvent");

        if (LOG.isDebugEnabled()) {
            LOG.debug("Dispatching {} event from endpoint {}", event, endpoint);
        }

        for (LeshanEventSource eventSource : eventSources) {
            if (eventSource.getEndpoint() == null || eventSource.getEndpoint().equals(endpoint)) {
                eventSource.sentEvent(event, data);
            }
        }
    }

    class ClientCoapListener implements CoapMessageListener {

        private final String endpoint;

        ClientCoapListener(String endpoint) {
        	System.out.println("EventServlet - ClientCoapListener - constructor");

            this.endpoint = endpoint;
        }

        @Override
        public void trace(CoapMessage message) {
        	System.out.println("EventServlet - ClientCoapListener - trace");
            String coapLog = EventServlet.this.gson.toJson(message);
            sendEvent(EVENT_COAP_LOG, coapLog, endpoint);
        }

    }

    private void cleanCoapListener(String endpoint) {
    	System.out.println("EventServlet - cleanCoapListener");

        // remove the listener if there is no more eventSources for this endpoint
        for (LeshanEventSource eventSource : eventSources) {
            if (eventSource.getEndpoint() == null || eventSource.getEndpoint().equals(endpoint)) {
                return;
            }
        }
        coapMessageTracer.removeListener(endpoint);
    }

    @Override
    protected EventSource newEventSource(HttpServletRequest req) {
    	System.out.println("EventServlet - newEventSource");

        String endpoint = req.getParameter(QUERY_PARAM_ENDPOINT);
        return new LeshanEventSource(endpoint);
    }

    private class LeshanEventSource implements EventSource {

        private String endpoint;
        private Emitter emitter;

        public LeshanEventSource(String endpoint) {
            this.endpoint = endpoint;
        }

        @Override
        public void onOpen(Emitter emitter) throws IOException {
        	System.out.println("EventServlet - LeshanEventSource - onOpen");

            this.emitter = emitter;
            eventSources.add(this);
            if (endpoint != null) {
                coapMessageTracer.addListener(endpoint, new ClientCoapListener(endpoint));
            }
        }

        @Override
        public void onClose() {
        	System.out.println("EventServlet - LeshanEventSource - onClose");

            cleanCoapListener(endpoint);
            eventSources.remove(this);
        }

        public void sentEvent(String event, String data) {
        	System.out.println("EventServlet - LeshanEventSource - sentEvent");

            try {
                emitter.event(event, data);
            } catch (IOException e) {
                e.printStackTrace();
                onClose();
            }
        }

        public String getEndpoint() {
        	
        	System.out.println("EventServlet - LeshanEventSource - getEndpoint");

            return endpoint;
        }
    }

    @SuppressWarnings("unused")
    private class RegUpdate {
        public Registration registration;
        public RegistrationUpdate update;
    }
}
