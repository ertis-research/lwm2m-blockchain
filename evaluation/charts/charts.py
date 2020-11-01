import plotly.graph_objs as go

N_CLIENTS = [10, 25, 50, 100]
N_APLICATIONS = [1, 2, 4, 8, 16]
N_ANOMALIES = [10, 25, 50, 100]
N_USERS = [10, 25, 50, 100]
#PRIMARY_PLOTLY = "#8395c1" #muted blue
ROYAL_BLUE = '#1f77b4' #royal blue
RED_BRICK = '#d62728' #red brick
GREEN = '#2ca02c'
PURPLE= '#9467bd'
BLUE_LINE = dict(color=ROYAL_BLUE)
BLUE_LINE_DASHED = dict(color=ROYAL_BLUE, dash='dash')
RED_LINE = dict(color=RED_BRICK)
RED_LINE_DASHED = dict(color=RED_BRICK, dash='dash')
GREEN_LINE = dict(color=GREEN, dash='dot')
PURPLE_LINE = dict(color=PURPLE, dash='dashdot')
# ClientStore charts

def leshanVsProposed(json_store, json_rest, bc_store, bc_rest):
  fig = go.Figure()

  fig.add_trace(go.Scatter(x=N_CLIENTS, y=json_store, mode="lines", name="LESHAN LWM2M (in-memory time)", line=BLUE_LINE))
  fig.add_trace(go.Scatter(x=N_CLIENTS, y=bc_store, mode="lines", name="PROPOSED SOLUTION (blockchain time)", line=RED_LINE))
  fig.add_trace(go.Scatter(x=N_CLIENTS, y=json_rest, mode="lines", name="LESHAN LWM2M (rest of the time)", line=BLUE_LINE_DASHED))
  fig.add_trace(go.Scatter(x=N_CLIENTS, y=bc_rest, mode="lines", name="PROPOSED SOLUTION (rest of the time)", line=RED_LINE_DASHED))

  fig.update_xaxes(title_text="<b>Number of clients registered</b>", range=[10, 100], tick0=10, dtick=10)
  fig.update_yaxes(title_text="<b>Average time per operation (ms)</b>", range=[0, 1700])

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white"
  )

  config = {'toImageButtonOptions': {
    'filename': f'leshanVsProposed',
  }}

  #fig.write_image("leshanVsProposed.pdf")

  fig.show(config=config)


def clientAddAndRemove(no_kubernetes, replica1, replica2, replica4, version):
  fig = go.Figure()

  fig.add_trace(go.Scatter(x=N_CLIENTS, y=no_kubernetes, mode="lines", name="NO KUBERNETES", line=BLUE_LINE))
  fig.add_trace(go.Scatter(x=N_CLIENTS, y=replica1, mode="lines", name="1 REPLICA", line=RED_LINE_DASHED))
  fig.add_trace(go.Scatter(x=N_CLIENTS, y=replica2, mode="lines", name="2 REPLICAS", line=GREEN_LINE))
  fig.add_trace(go.Scatter(x=N_CLIENTS, y=replica4, mode="lines", name="4 REPLICAS", line=PURPLE_LINE))

  fig.update_xaxes(title_text="<b>Number of clients</b>", range=[10, 100], tick0=10, dtick=10)
  fig.update_yaxes(title_text="<b>Average time per operation (s)</b>", range=[0, 40], tick0=0, dtick=10)

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white",
  )

  config = {'toImageButtonOptions': {
    'filename': f'client{version}',
  }}

  #fig.write_image(f'client{version}.pdf')

  fig.show(config=config)

def getAllClients(no_kubernetes, replica1, replica2, replica4):
  fig = go.Figure()

  fig.add_trace(go.Scatter(x=N_CLIENTS, y=no_kubernetes, mode="lines", name="NO KUBERNETES", line=BLUE_LINE))
  fig.add_trace(go.Scatter(x=N_CLIENTS, y=replica1, mode="lines", name="1 REPLICA", line=RED_LINE_DASHED))
  fig.add_trace(go.Scatter(x=N_CLIENTS, y=replica2, mode="lines", name="2 REPLICAS", line=GREEN_LINE))
  fig.add_trace(go.Scatter(x=N_CLIENTS, y=replica4, mode="lines", name="4 REPLICAS", line=PURPLE_LINE))

  fig.update_xaxes(title_text="<b>Clients retrieved</b>", range=[10, 100], tick0=10, dtick=10)
  fig.update_yaxes(title_text="<b>Total time (ms)</b>", range=[130, 270], tick0=0, dtick=10)

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white"
  )

  config = {'toImageButtonOptions': {
    'filename': f'getAllClients',
  }}

  #fig.write_image("getAllClients.pdf")

  fig.show(config=config)

# AnomalyStore charts

def addCriticalInformation(no_kubernetes, replica1, replica2, replica4, version):
  fig = go.Figure()

  fig.add_trace(go.Scatter(x=N_APLICATIONS, y=no_kubernetes, mode="lines", name="NO KUBERNETES", line=BLUE_LINE))
  fig.add_trace(go.Scatter(x=N_APLICATIONS, y=replica1, mode="lines", name="1 REPLICA", line=RED_LINE_DASHED))
  fig.add_trace(go.Scatter(x=N_APLICATIONS, y=replica2, mode="lines", name="2 REPLICAS", line=GREEN_LINE))
  fig.add_trace(go.Scatter(x=N_APLICATIONS, y=replica4, mode="lines", name="4 REPLICAS", line=PURPLE_LINE))

  fig.update_xaxes(title_text="<b>Number of simultaneous applications </b>", range=[1, 16], tick0=0, dtick=2)
  fig.update_yaxes(title_text="<b>Average time per operation (s)</b>", range=[20, 40], tick0=20, dtick=5)

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white",
  )

  config = {'toImageButtonOptions': {
    'filename': f'addCriticalInformation{version}',
  }}

  fig.write_image(f'addCriticalInformation{version}.pdf')

  fig.show(config=config)

def getAllCriticalInformation(no_kubernetes, replica1, replica2, replica4):
  fig = go.Figure()

  fig.add_trace(go.Scatter(x=N_ANOMALIES, y=no_kubernetes, mode="lines", name="NO KUBERNETES", line=BLUE_LINE))
  fig.add_trace(go.Scatter(x=N_ANOMALIES, y=replica1, mode="lines", name="1 REPLICA", line=RED_LINE_DASHED))
  fig.add_trace(go.Scatter(x=N_ANOMALIES, y=replica2, mode="lines", name="2 REPLICAS", line=GREEN_LINE))
  fig.add_trace(go.Scatter(x=N_ANOMALIES, y=replica4, mode="lines", name="4 REPLICAS", line=PURPLE_LINE))

  fig.update_xaxes(title_text="<b>Critical information entries retrived</b>", range=[10, 100], tick0=10, dtick=10)
  fig.update_yaxes(title_text="<b>Total time (ms)</b>", range=[100, 300])

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white"
  )

  config = {'toImageButtonOptions': {
    'filename': f'getAllCriticalInformation',
  }}

  #fig.write_image("getAllCriticalInformation.pdf")

  fig.show(config=config)

# UserStore charts

def usersAddAndUpdate(no_kubernetes, replica1, replica2, replica4, version):
  fig = go.Figure()

  fig.add_trace(go.Scatter(x=N_USERS, y=no_kubernetes, mode="lines", name="NO KUBERNETES", line=BLUE_LINE))
  fig.add_trace(go.Scatter(x=N_USERS, y=replica1, mode="lines", name="1 REPLICA", line=RED_LINE_DASHED))
  fig.add_trace(go.Scatter(x=N_USERS, y=replica2, mode="lines", name="2 REPLICAS", line=GREEN_LINE))
  fig.add_trace(go.Scatter(x=N_USERS, y=replica4, mode="lines", name="4 REPLICAS", line=PURPLE_LINE))

  fig.update_xaxes(title_text="<b>Number of users</b>", range=[10, 100], tick0=10, dtick=10)
  fig.update_yaxes(title_text="<b>Average time per operation (s)</b>", range=[0, 40], tick0=0, dtick=10)

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white",
  )

  config = {'toImageButtonOptions': {
    'filename': f'users{version}',
  }}
  
  #fig.write_image(f'users{version}.pdf')
  
  fig.show(config=config)

def validateLogin(no_kubernetes, replica1, replica2, replica4):
  fig = go.Figure()

  fig.add_trace(go.Scatter(x=N_CLIENTS, y=no_kubernetes, mode="lines", name="NO KUBERNETES", line=BLUE_LINE))
  fig.add_trace(go.Scatter(x=N_CLIENTS, y=replica1, mode="lines", name="1 REPLICA", line=RED_LINE_DASHED))
  fig.add_trace(go.Scatter(x=N_CLIENTS, y=replica2, mode="lines", name="2 REPLICAS", line=GREEN_LINE))
  fig.add_trace(go.Scatter(x=N_CLIENTS, y=replica4, mode="lines", name="4 REPLICAS", line=PURPLE_LINE))

  fig.update_xaxes(title_text="<b>Users stored on smart contract</b>", range=[10, 100], tick0=10, dtick=10)
  fig.update_yaxes(title_text="<b>Average time per login attempt (ms)</b>", range=[140, 180], tick0=0, dtick=10)

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white",
  )

  config = {'toImageButtonOptions': {
    'filename': f'validateLogin',
  }}

  #fig.write_image("validateLogin.pdf")

  fig.show(config=config)