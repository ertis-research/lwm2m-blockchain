export const timestampToDate = (timestamp: number) => {
  var date = new Date(timestamp);
  var dateDisplay = date.toLocaleString('es-ES', {
    day: 'numeric',
    month: 'numeric',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
  });
  return dateDisplay;
};