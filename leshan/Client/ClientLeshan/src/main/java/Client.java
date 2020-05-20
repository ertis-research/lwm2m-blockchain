import java.util.Random;

import org.eclipse.leshan.LwM2mId;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.object.Device;
import org.eclipse.leshan.client.object.Security;
import org.eclipse.leshan.client.object.Server;
import org.eclipse.leshan.client.request.ServerIdentity;
import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.request.BindingMode;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

public class Client {
	public static void main(String[] args) {
		
		String endpoint = "ivanEndpoint1" ; // choose an endpoint name
		byte[] idByte = asciiToByte("ivanbs1");
		byte[] keyByte = hexToByte("654321");
		String url = "coaps://localhost:5684";		
		
		LeshanClientBuilder builder = new LeshanClientBuilder(endpoint);
		
		// create objects
		ObjectsInitializer initializer = new ObjectsInitializer();
		initializer.setInstancesForObject(LwM2mId.SECURITY, Security.pskBootstrap(url, idByte, keyByte));
		initializer.setInstancesForObject(LwM2mId.SERVER, new Server(12345, 5 * 60, BindingMode.U, false));
		initializer.setInstancesForObject(LwM2mId.DEVICE, new Device("Eclipse Leshan", "model12345", "12345", "U"));
		initializer.setInstancesForObject(7, new SensorMeasure());

		// add it to the client
		builder.setObjects(initializer.createAll());
		LeshanClient client = builder.build();
		client.start();
	}

	public static class SensorMeasure extends BaseInstanceEnabler {

		@Override
		public ReadResponse read(ServerIdentity identity, int resourceid) {
			System.out.println("ReadResponse "+resourceid + " " +identity.getPskIdentity());
			
			switch (resourceid) {
			case 0:
				return ReadResponse.success(resourceid, getRandom());
			case 14:
				return ReadResponse.success(resourceid, 14);
			}
			return ReadResponse.notFound();
		}

		@Override
		public WriteResponse write(ServerIdentity identity, int resourceid, LwM2mResource value) {
			System.out.println("WriteResponse "+resourceid + " " +identity.getPskIdentity());

			switch (resourceid) {
			case 15:
				return WriteResponse.success();
			}
			return WriteResponse.notFound();
		}

		@Override
		public ExecuteResponse execute(ServerIdentity identity, int resourceid, String params) {
			System.out.println("ExecuteResponse "+resourceid + " " +identity.getPskIdentity());

			switch (resourceid) {
			case 12:
				return ExecuteResponse.success();
			}
			return ExecuteResponse.notFound();
		}
		
		private int getRandom() {
			Random rand = new Random();
			return rand.nextInt(100);
		}
	}
	
	private static byte[] hexToByte(String s) {
		int len = s.length();
		byte[] data = new byte[len/2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}
	
	private static byte[] asciiToByte(String ascii){
		byte[] bytearray = new byte[ascii.length()];
		for (int i = 0; i < ascii.length(); i++) {
			bytearray[i] = (byte) Character.codePointAt(ascii, i);
		}
		return bytearray;
	};
}
