import plotly.graph_objs as go

N_CLIENTS = [10, 25, 50, 100, 500]
N_ANOMALIES = [10, 25, 50, 100]
N_ANOMALIES_BAR = ["10", "25", "50", "100"]
N_USERS = [10, 25, 50, 100]
N_USERS_BAR = ["10", "25", "50", "100"]
LINE_DASHED = dict(dash='dash')

# ClientStore charts

def jsonAndBc(json, bc):
  fig = go.Figure()

  fig.add_trace(go.Scatter(x=N_CLIENTS, y=json, mode="lines", name="JSON"))
  fig.add_trace(go.Scatter(x=N_CLIENTS, y=bc, mode="lines", name="BLOCKCHAIN", line=LINE_DASHED))

  fig.update_xaxes(title_text="Number of clients retrieved", range=[10, 500], tick0=10, dtick=49)
  fig.update_yaxes(title_text="Total elapsed time (ms)", range=[0, 500])

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white"
  )

  config = {'toImageButtonOptions': {
    'filename': f'jsonAndBc',
  }}

  fig.show(config=config)

def clientAddAndRemove(add, remove):
  fig = go.Figure()

  fig.add_trace(go.Scatter(x=N_CLIENTS, y=add, mode="lines", name="ADD"))
  fig.add_trace(go.Scatter(x=N_CLIENTS, y=remove, mode="lines", name="REMOVE", line=LINE_DASHED))

  fig.update_xaxes(title_text="Number of clients", range=[10, 500], tick0=10, dtick=49)
  fig.update_yaxes(title_text="Average time per operation (s)", range=[0, 40], tick0=0, dtick=10)

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white"
  )

  config = {'toImageButtonOptions': {
    'filename': f'clientAddAndRemove',
  }}

  fig.show(config=config)

# AnomalyStore charts

def addAnomalyLine(anomalies):
  fig = go.Figure()

  fig.add_trace(go.Scatter(x=N_ANOMALIES, y=anomalies, mode="lines"))

  fig.update_xaxes(title_text="Anomalies added", range=[10, 100], tick0=10, dtick=10)
  fig.update_yaxes(title_text="Average time per operation (s)", range=[0, 40], tick0=0, dtick=10)

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white"
  )

  config = {'toImageButtonOptions': {
    'filename': f'addAnomaly',
  }}

  fig.show(config=config)

def addAnomaly(anomalies):
  fig = go.Figure()

  fig.add_trace(go.Bar(x=N_ANOMALIES_BAR, y=anomalies))

  fig.update_xaxes(title_text="Anomalies added", type="category")
  fig.update_yaxes(title_text="Average time per operation (s)", range=[0, 35], tick0=0, dtick=5)

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white",
    width=500
  )

  config = {'toImageButtonOptions': {
    'filename': f'addAnomalyBar',
  }}

  fig.show(config=config)

def getAllAnomaliesLine(anomalies):
  fig = go.Figure()

  fig.add_trace(go.Scatter(x=N_ANOMALIES, y=anomalies, mode="lines"))

  fig.update_xaxes(title_text="Anomalies retrieved", range=[10, 100], tick0=10, dtick=10)
  fig.update_yaxes(title_text="Total time (ms)", range=[0, 500])

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white"
  )

  config = {'toImageButtonOptions': {
    'filename': f'getAllAnomalies',
  }}

  fig.show(config=config)

def getAllAnomalies(anomalies):
  fig = go.Figure()

  fig.add_trace(go.Bar(x=N_ANOMALIES_BAR, y=anomalies))

  fig.update_xaxes(title_text="Anomalies retrieved", type="category")
  fig.update_yaxes(title_text="Total time (ms)", range=[0, 250], tick0=0, dtick=50)

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white",
    width=500
  )

  config = {'toImageButtonOptions': {
    'filename': f'getAllAnomaliesBar',
  }}

  fig.show(config=config)

# UserStore charts

def usersAddAndUpdate(add, update):
  fig = go.Figure()

  fig.add_trace(go.Scatter(x=N_USERS, y=add, mode="lines", name="ADD"))
  fig.add_trace(go.Scatter(x=N_USERS, y=update, mode="lines", name="UPDATE", line=LINE_DASHED))

  fig.update_xaxes(title_text="Number of clients", range=[10, 100], tick0=10, dtick=10)
  fig.update_yaxes(title_text="Average time per operation (s)", range=[0, 40], tick0=0, dtick=10)

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white"
  )

  config = {'toImageButtonOptions': {
    'filename': f'usersAddAndUpdate',
  }}
  
  fig.show(config=config)

def validateLogin(validations):
  fig = go.Figure()

  fig.add_trace(go.Bar(x=N_USERS_BAR, y=validations))

  fig.update_xaxes(title_text="Users stored on smart contract", type="category")
  fig.update_yaxes(title_text="Average time per login attempt (ms)", range=[0, 200], tick0=0, dtick=50)

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white",
    width=500
  )

  config = {'toImageButtonOptions': {
    'filename': f'validateLogin',
  }}

  fig.show(config=config)