
export function formatDate(dateString) {
    const date = new Date(dateString);

    const monthNames = [
      "January", "February", "March",
      "April", "May", "June", "July",
      "August", "September", "October",
      "November", "December"
    ];
  
    const monthIndex = date.getMonth();
    const year = date.getFullYear();
  
    return monthNames[monthIndex] + ' ' + year;
}
  
export function formatDateTime(dateTimeString) {
  const date = new Date(dateTimeString);

  const monthNames = [
    "Jan", "Feb", "Mar", "Apr",
    "May", "Jun", "Jul", "Aug", 
    "Sep", "Oct", "Nov", "Dec"
  ];

  const monthIndex = date.getMonth();
  const year = date.getFullYear();
  let minutes = date.getMinutes();
  let result = '';

  if (minutes < 10) {
      minutes = '0' +  minutes;
  }


  let hours = date.getHours();

    if (hours < 10) {
        hours = '0' +  hours;
    }

  if (hours === '00' && minutes === '00') {
      result = date.getDate() + ' ' + monthNames[monthIndex] + ' ' + year;
  }else{
      result = date.getDate() + ' ' + monthNames[monthIndex] + ' ' + year + ' - ' + hours + ':' + minutes;
  }

  return result;

}  