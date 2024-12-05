export function formatDate(dateStr: string): string {
  // Create a Date object from the provided string
  const date = new Date(dateStr);

  // Check if the date is valid
  if (isNaN(date.getTime())) {
    throw new Error('Invalid date string');
  }

  // Format the date using toLocaleString() for a readable format
  return date.toLocaleString('vi-VN', {
    year: '2-digit',
    month: 'numeric',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
  });
}
