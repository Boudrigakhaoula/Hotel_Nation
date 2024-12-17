import { Component, OnInit } from '@angular/core';
import { HotelService } from '../../../Service/hotel.service';
import { Hotel } from '../../../models/hotel.interface';

@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {
  hotels: Hotel[] = [];

  constructor(private hotelService: HotelService) { }

  ngOnInit(): void {
    this.fetchHotels();
  }

  fetchHotels(): void {
    this.hotelService.getAllHotels().subscribe((data: Hotel[]) => {
      this.hotels = data;
    });
  }

  fetchHotelsByLocation(location: string): void {
    this.hotelService.getHotelsByLocation(location).subscribe((data: Hotel[]) => {
      this.hotels = data;
    });
  }
}
