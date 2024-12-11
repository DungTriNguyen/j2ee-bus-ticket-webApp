export interface TicKetFullInfo {
    ticketId: number;
    seatNumber: string;
    departureDate: string;
    phoneNumber: string;
    accountUsername: string;
    paymentDate: string;
    originalAmount: number;
    discountAmount: number | null ;
    finalAmount: number;
    paymentMethod: string;
    status: string;
    dayOfWeek: string;
    departureTime: string;
    departureLocation: string;
    destinationLocation: string;
    distanceKm: number;
    tripTime: string;
    distinationTime: string;
  }