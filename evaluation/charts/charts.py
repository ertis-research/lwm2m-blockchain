import plotly.graph_objs as go

N_CLIENTS = [10, 25, 50, 100, 500]
N_ANOMALIES = [10, 25, 50, 100]
N_ANOMALIES_BAR = ["10", "25", "50", "100"]
N_USERS = [10, 25, 50, 100]
N_USERS_BAR = ["10", "25", "50", "100"]
#PRIMARY_PLOTLY = "#8395c1" #muted blue
ROYAL_BLUE = '#1f77b4' #royal blue
BLUE_LINE = dict(color=ROYAL_BLUE)
RED_LINE = dict(color='#d62728', dash='dash') #red brick

# ClientStore charts

def leshanVsProposed(json, bc):
  fig = go.Figure()

  fig.add_trace(go.Scatter(x=N_CLIENTS, y=json, mode="lines", name="ECLIPSE LESHAN", line=BLUE_LINE))
  fig.add_trace(go.Scatter(x=N_CLIENTS, y=bc, mode="lines", name="PROPOSED SOLUTION", line=RED_LINE))

  fig.update_xaxes(title_text="<b>Number of clients retrieved</b>", range=[10, 500], tick0=10, dtick=49)
  fig.update_yaxes(title_text="<b>Total elapsed time (ms)</b>", range=[0, 500])

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white"
  )

  config = {'toImageButtonOptions': {
    'filename': f'leshanVsProposed',
  }}

  #fig.write_image("leshanVsProposed.pdf")

  fig.show(config=config)

def clientAddAndRemove(add, remove):
  fig = go.Figure()

  fig.add_trace(go.Scatter(x=N_CLIENTS, y=add, mode="lines", name="ADD", line=BLUE_LINE))
  fig.add_trace(go.Scatter(x=N_CLIENTS, y=remove, mode="lines", name="REMOVE", line=RED_LINE))

  fig.update_xaxes(title_text="<b>Number of clients</b>", range=[10, 500], tick0=10, dtick=49)
  fig.update_yaxes(title_text="<b>Average time per operation (s)</b>", range=[0, 40], tick0=0, dtick=10)

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white"
  )

  config = {'toImageButtonOptions': {
    'filename': f'clientAddAndRemove',
  }}

  #fig.write_image("clientAddAndRemove.pdf")

  fig.show(config=config)

# AnomalyStore charts

def addCriticalInformationLine(anomalies):
  fig = go.Figure()

  fig.add_trace(go.Scatter(x=N_ANOMALIES, y=anomalies, mode="lines", line=BLUE_LINE))

  fig.update_xaxes(title_text="<b>Critical information entries added</b>", range=[10, 100], tick0=10, dtick=10)
  fig.update_yaxes(title_text="<b>Average time per operation (s)</b>", range=[0, 40], tick0=0, dtick=10)

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white"
  )

  config = {'toImageButtonOptions': {
    'filename': f'addCriticalInformation',
  }}

  #fig.write_image("addCriticalInformationLine.pdf")

  fig.show(config=config)

def addCriticalInformation(anomalies):
  fig = go.Figure()

  fig.add_trace(go.Bar(x=N_ANOMALIES_BAR, y=anomalies, marker_color=ROYAL_BLUE))

  fig.update_xaxes(title_text="<b>Critical information entries added</b>", type="category")
  fig.update_yaxes(title_text="<b>Average time per operation (s)</b>", range=[0, 35], tick0=0, dtick=5)

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white",
    width=500
  )

  config = {'toImageButtonOptions': {
    'filename': f'addCriticalInformationBar',
  }}

  fig.write_image("addCriticalInformation.pdf")

  fig.show(config=config)

def getAllCriticalInformationLine(anomalies):
  fig = go.Figure()

  fig.add_trace(go.Scatter(x=N_ANOMALIES, y=anomalies, mode="lines", line=BLUE_LINE))

  fig.update_xaxes(title_text="<b>Critical information entries retrived</b>", range=[10, 100], tick0=10, dtick=10)
  fig.update_yaxes(title_text="<b>Total time (ms)</b>", range=[0, 500])

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white"
  )

  config = {'toImageButtonOptions': {
    'filename': f'getAllCriticalInformation',
  }}

  #fig.write_image("getAllCriticalInformationLine.pdf")

  fig.show(config=config)

def getAllCriticalInformation(anomalies):
  fig = go.Figure()

  fig.add_trace(go.Bar(x=N_ANOMALIES_BAR, y=anomalies, marker_color=ROYAL_BLUE))

  fig.update_xaxes(title_text="<b>Critical information entries retrived</b>", type="category")
  fig.update_yaxes(title_text="<b>Total time (ms)</b>", range=[0, 250], tick0=0, dtick=50)

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white",
    width=500
  )

  config = {'toImageButtonOptions': {
    'filename': f'getAllCriticalInformationBar',
  }}

  fig.write_image("getAllCriticalInformation.pdf")

  fig.show(config=config)

# UserStore charts

def usersAddAndUpdate(add, update):
  fig = go.Figure()

  fig.add_trace(go.Scatter(x=N_USERS, y=add, mode="lines", name="ADD", line=BLUE_LINE))
  fig.add_trace(go.Scatter(x=N_USERS, y=update, mode="lines", name="UPDATE", line=RED_LINE))

  fig.update_xaxes(title_text="<b>Number of users</b>", range=[10, 100], tick0=10, dtick=10)
  fig.update_yaxes(title_text="<b>Average time per operation (s)</b>", range=[0, 40], tick0=0, dtick=10)

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white"
  )

  config = {'toImageButtonOptions': {
    'filename': f'usersAddAndUpdate',
  }}
  
  #fig.write_image("usersAddAndUpdate.pdf")
  
  fig.show(config=config)

def validateLogin(validations):
  fig = go.Figure()

  fig.add_trace(go.Bar(x=N_USERS_BAR, y=validations, marker_color=ROYAL_BLUE))

  fig.update_xaxes(title_text="<b>Users stored on smart contract</b>", type="category")
  fig.update_yaxes(title_text="<b>Average time per login attempt (ms)</b>", range=[0, 200], tick0=0, dtick=50)

  fig.layout.update(
    legend=dict(y=1.15, orientation="h"),
    template="plotly_white",
    width=500
  )

  config = {'toImageButtonOptions': {
    'filename': f'validateLogin',
  }}

  #fig.write_image("validateLogin.pdf")

  fig.show(config=config)