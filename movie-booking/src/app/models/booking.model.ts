export interface Booking {
  id?: number;
  movieId: number;
  movieName?: string;
  customerName: string;
  customerEmail: string;
  customerPhone: string;
  numberOfSeats: number;
  bookingDate?: string;
  totalPrice?: number;
}
