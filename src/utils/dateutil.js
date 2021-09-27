export function getTime (time) {
    var date = new Date(time)
    var y = date.getFullYear()
    var m = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1);
    var d = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
    var h = (date.getHours() < 10 ? '0' + (date.getHours()) : date.getHours());
    var mm = (date.getMinutes() < 10 ? '0' + (date.getMinutes()) : date.getMinutes());
    var s = (date.getSeconds() < 10 ? '0' + (date.getSeconds()) : date.getSeconds());
    return y + '-' + m + '-' + d +' ' + h + ':' + mm + ':' + s;
}