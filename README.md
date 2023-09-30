![](etc/lb-2.png)

# Introduction

**LiveBoards** allows real-time visualization of your data as events occur, using one single application. Events are published using a RESTful API and stored into an in-memory database loosely following a tabular format familiar from relational databases. Changes in the database are immediately picked up by the filter and aggregation pipelines, and results are calculated on-the-fly for every set of defined boards (views) to your data. Completed results, again, are immediately pushed to boards where visualizations are automatically updated, without the need for any interaction.

![](etc/lb-1.png)

# Getting Started

## Requirements

- Make sure you have Java Development Kit version **17** or newer installed
- Make sure you have Apache Ant **1.10** or newer installed

## Building from Source

**Note!** Examples for MacOS, Unix, & Linux.

1. Clone this repository (and check out a specific version if needed):
   ```
   $ git clone https://github.com/rtswstudio/LiveBoards
   $ cd LiveBoards
   ```
2. Compile the code and create a distribution package:
   ```
   $ ant clean
   $ ant
   ```
3. Modify the `dist/liveboards-config.json` if needed (see schema and example below)
4. Start the application (`CTRL-C` to stop):
   ```
   $ cd dist
   $ sh ./run.sh
   ```
5. Check system health using `curl` (or open the URL in your browser):
   ```
   $ curl "http://localhost:8888/api/1/health"
   ```
   You should see something resembling:
   ````json
   {
    "boards": {
      "count": 3
    },
    "tables": {
      "count": 3
    },
    "memory": {
      "free": 56280800,
      "total": 83886080,
      "max": 8589934592
    }
    ,
    "version": "0.1",
    "processors": {
      "available": 16
    },
    "time": "24.09.2023 20:02:36"
   }
   ````
6. Start creating tables and boards:
   - Example tables are found under `examples/tables`
   - Example boards are found under `examples/boards`
   - Example data push clients are found under `examples`

# Documentation

Please see the [Wiki Pages](https://github.com/rtswstudio/LiveBoards/wiki) for complete documentation.

# License

**LiveBoards** is licensed under the [MIT License](https://github.com/rtswstudio/LiveBoards/blob/master/LICENSE.md).