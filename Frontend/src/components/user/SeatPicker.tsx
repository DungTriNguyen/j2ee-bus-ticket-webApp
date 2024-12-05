import { Button } from "flowbite-react";

export default function SeatPicker() {
  return (
    <div className="flex gap-6">
      <div className="grid grid-cols-3 gap-2">
        {Array.from({ length: 18 }, (_, i) => (
          <Button size='xs' key={i} color={i % 2 == 0 ? 'failure' : 'blue'} >
            A{i + 1}
          </Button>
        ))}
      </div>
    </div>
  )
}