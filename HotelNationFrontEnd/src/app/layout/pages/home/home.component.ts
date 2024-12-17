import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HotelService } from '../../../Service/hotel.service';
import { Hotel } from '../../../models/hotel.interface';

@Component({
  selector: 'app-home',
  standalone: false,
  
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  searchLocation: string = '';
  hotels: Hotel[] = [];

  constructor(
    private hotelService: HotelService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  searchHotels(): void {
    if (this.searchLocation.trim()) {
      const formattedLocation = this.searchLocation.trim()
        .toLowerCase()
        .split(' ')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
        .join(' ');

      this.hotelService.getHotelsByLocation(formattedLocation).subscribe({
        next: (data: Hotel[]) => {
          this.hotels = data;
          this.router.navigate(['/destination'], { 
            queryParams: { location: formattedLocation }
          });
        },
        error: (error) => {
          console.error('Error searching hotels:', error);
        }
      });
    }
  }
}
