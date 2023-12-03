/**
 * Controller for the website.
 */

// global variables
var chart;
var rainSeries = [];
var levelSeries = [];

window.chartColors = {
  red: 'rgb(255, 99, 132)',
  orange: 'rgb(255, 159, 64)',
  yellow: 'rgb(255, 205, 86)',
  green: 'rgb(92, 184, 92)',
  blue: 'rgb(51, 122, 183)',
  purple: 'rgb(153, 102, 255)',
  grey: 'rgb(231,233,237)'
};

var color = Chart.helpers.color;
var chartData = {
  datasets: [{
    label: "Wasserstand",
    borderColor: window.chartColors.green,
    backgroundColor: color(window.chartColors.green).alpha(0.2).rgbString(),
    yAxisID: "y-axis-1",
    data: []
  },
  {
    label: "Regen",
    borderColor: window.chartColors.blue,
    backgroundColor: color(window.chartColors.blue).alpha(0.2).rgbString(),
    yAxisID: "y-axis-2",
    data: []
  }]
};

// Update the chart with the data from begin to the end.
function updateChart(begin, end) {

  $.getJSON("/api/tank/measurements?query=return&sensor=TankLevel&begin=" + begin + "&end=" + end + "&sort=-date", function (data) {

    levelSeries = [];
   console.log("Start");
//    data = [{"id":"656cd221e4b0e100005f6347","timestamp":"2023-12-03T20:08:16.000Z","sensor":"TankLevel","value":56.0,"unit":"%"},{"id":"656cc411e4b027a622f28d0c","timestamp":"2023-12-03T19:08:16.000Z","sensor":"TankLevel","value":57.0,"unit":"%"},{"id":"656cb601e4b027a622f28d0a","timestamp":"2023-12-03T18:08:16.000Z","sensor":"TankLevel","value":57.0,"unit":"%"},{"id":"656ca7f1e4b027a622f28d08","timestamp":"2023-12-03T17:08:16.000Z","sensor":"TankLevel","value":56.0,"unit":"%"}]
    console.log(data);
//    console.log("TS 1");
//      var ts = moment(data[0].timestamp).format("DD.MM.YYYY HH:mm:ss");
//    console.log("TS 2");
//      console.log(ts);
//    console.log("TS 3");

    $.each(data, function (key, val) {
      //      var ts = moment(val.timestamp, "MMM D, YYYY hh:mm:ss A").format("YYYY-MM-DD HH:mm:ss");
      var ts = moment(val.timestamp).format("DD.MM.YYYY HH:mm:ss");
      console.log(ts);
      levelSeries.push({ x: ts, y: val.value });
    });

    chartData.datasets[0].data = levelSeries;
    chart.update();
  });

  $.getJSON("/api/tank/measurements?query=return&sensor=Precipitation&begin=" + begin + "&end=" + end + "&sort=-date", function (data) {

    rainSeries = [];

    $.each(data, function (key, val) {
      //      var ts = moment(val.timestamp, "MMM D, YYYY hh:mm:ss A").format("YYYY-MM-DD HH:mm:ss");
      var ts = moment(val.timestamp, "MMM D, YYYY hh:mm:ss A").format("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
      rainSeries.push({ x: ts, y: val.value });
    });

    chartData.datasets[1].data = rainSeries;
    chart.update();
  });
}

// Register event handlers for the chart update.
$('#action-3-days').click(function (e) {
  e.preventDefault();

  var begin = moment().subtract(3, 'days').format("YYYY-MM-DD");
  var end = moment().format("YYYY-MM-DD");

  updateChart(begin, end);
})

$('#action-1-week').click(function (e) {
  e.preventDefault();

  var begin = moment().subtract(7, 'days').format("YYYY-MM-DD");
  var end = moment().format("YYYY-MM-DD");

  updateChart(begin, end);
})

$('#action-1-month').click(function (e) {
  e.preventDefault();

  var begin = moment().subtract(1, 'months').format("YYYY-MM-DD");
  var end = moment().format("YYYY-MM-DD");

  updateChart(begin, end);
})

$('#action-6-months').click(function (e) {
  e.preventDefault();

  var begin = moment().subtract(6, 'months').format("YYYY-MM-DD");
  var end = moment().format("YYYY-MM-DD");

  updateChart(begin, end);
})
