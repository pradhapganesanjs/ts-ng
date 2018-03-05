import { Component, OnInit } from '@angular/core';
import { SessionProcessor } from '@app/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  signedName: string;
  constructor(private sessPros: SessionProcessor) { }

  ngOnInit() {
    this.sessPros.getUserSignedin().subscribe( user => this.signedName = user.userName );
  }

}
