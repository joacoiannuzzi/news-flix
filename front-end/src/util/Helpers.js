export function formatDateTime(dateTimeString) {
    const date = new Date(dateTimeString);

    const monthNames = [
        "Enero", "Febrero", "Marzo", "Abril",
        "Mayo", "Junio", "Julio", "Agosto",
        "Septiembre", "Octubre", "Noviembre", "Diciembre"
    ];

    const monthIndex = date.getMonth();
    const year = date.getFullYear();
    const minutes = date.getMinutes();
    const hours = date.getHours();

    const finalMin = minutes < 10
        ? '0' + minutes.toString()
        : minutes.toString()


    const finalHours = hours < 10
        ? '0' + hours.toString()
        : hours.toString()

    return finalHours === '00' && finalMin === '00'
        ? date.getDate() + ' ' + monthNames[monthIndex] + ' ' + year
        : date.getDate() + ' ' + monthNames[monthIndex] + ' ' + year + ' - ' + finalHours + ':' + finalMin;

}  