# Charts

In order to execute the Jupyter notebook is necessary to install the following packages:

```bash
pip install plotly
pip install psutil
```

Then you will have to install [orca](https://github.com/plotly/orca). Orca is the backbone of Plotly's Image Server, needed to generate chart on PDF format. After installing orca you have to start it with the next command:

```bash
orca serve
```

Finally you can execute the Jupyter notebook [ChartGenerator](ChartGenerator.ipynb)