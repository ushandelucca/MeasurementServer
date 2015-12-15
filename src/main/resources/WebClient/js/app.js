$("input[type='checkbox']").bootstrapSwitch();

$(document).ready(function () {

    // TODO load the data from the server
    // show please wait

    g1 = new Dygraph(
        document.getElementById("chart"),
        data_temp, {
            customBars: false,
            ylabel: 'Liter',
            legend: 'always',
            labelsDivStyles: {
                'textAlign': 'right'
            },
            showRangeSelector: true
        }
    );

});