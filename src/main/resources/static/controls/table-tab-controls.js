function insertRowsToTable(sensorData) {
    let table = document.getElementById('sensors-tbl');

    sensorData.forEach(sensorInfo => {
        let row = table.insertRow();
        insertCells(row, sensorInfo);
    })
}

function insertCells(row, sensorInfo) {
    objectValuesToArray(sensorInfo).forEach(value => {
        let cell = row.insertCell();
        cell.innerHTML = value.toString();
    });
}

function objectValuesToArray(obj) {
    let values = Object.values(obj);
    let result = [];

    values.forEach(val => {
        if (isObject(val)) {
            let subRes = objectValuesToArray(val);
            subRes.forEach(item => result.push(item))
        } else {
            result.push(val.toString());
        }
    });

    return result;
}

function isObject(val) {
    if (val === null) {
        return false;
    }
    return ((typeof val === 'function') || (typeof val === 'object'));
}