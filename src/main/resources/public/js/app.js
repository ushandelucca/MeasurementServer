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

// Register event handlers
$('#action-1-week').click(function (e) {
  e.preventDefault();

  var begin = moment().subtract(3, 'days').format("YYYY-MM-DD");
  var end = moment().format("YYYY-MM-DD");

  $.getJSON( "/api/tank/measurements?query=return&sensor=Precipitation&begin=" + begin + "&end=" + end + "&sort=-date", function( data ) {

    $.each( data, function( key, val ) {
      var ts = moment(val.timestamp, "MMM D, YYYY hh:mm:ss A").format("YYYY-MM-DD HH:mm:ss");
      rainSeries.push({x:ts, y:val.value});
    });

    chartData.datasets[1].data = rainSeries;
    chart.update();
  });

  console.log("Event: " + e.value);

})
