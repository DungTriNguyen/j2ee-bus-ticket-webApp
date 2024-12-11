import { Button } from 'flowbite-react';
import React from 'react';

interface SeatPickerProps {
  seatAvailability: { [key: string]: boolean };
  onSelectionChange: (seats: string[], total: number) => void;
  seatPrice: number;
}

const SeatPicker: React.FC<SeatPickerProps> = ({
  seatAvailability,
  onSelectionChange,
  seatPrice,
}) => {
  const [selectedSeats, setSelectedSeats] = React.useState<string[]>([]);
  const [totalAmount, setTotalAmount] = React.useState<number>(0);

  const handleSeatClick = (seat: string) => {
    if (seatAvailability[seat]) {
      // Only allow selection if the seat is empty
      const newSelectedSeats = selectedSeats.includes(seat)
        ? selectedSeats.filter((s) => s !== seat) // Deselect if already selected
        : [...selectedSeats, seat]; // Select if not already selected

      setSelectedSeats(newSelectedSeats);
      const newTotalAmount = newSelectedSeats.length * seatPrice;
      setTotalAmount(newTotalAmount);

      onSelectionChange(newSelectedSeats, newTotalAmount); // Pass back the updated selection and total
    }
  };

  const sortedSeats = Object.keys(seatAvailability).sort(); // Sort seat IDs

  return (
    <div className='grid grid-cols-4 gap-4'>
      {sortedSeats.map((seat) => (
        <Button
          size='sm'
          key={seat}
          onClick={() => handleSeatClick(seat)}
          className={`seat ${seatAvailability[seat] ? 'bg-gray-300' : 'bg-red-800'} 
                      ${selectedSeats.includes(seat) ? 'bg-blue-500' : ''} 
                    `}
          disabled={!seatAvailability[seat]}
        >
          {seat.replace(/^\d+_/, "")}
        </Button>
      ))}
    </div>
  );
};

export default SeatPicker;
