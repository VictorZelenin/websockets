function now() {
    return new Date().toISOString();
}

function randColor() {
    return '#' + Math.floor(Math.random() * 16777215).toString(16);
}