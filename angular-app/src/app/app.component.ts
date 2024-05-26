import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';



interface User {
  id: number; 
  userName: string;
  displayPicture: string;
}
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,CommonModule,FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'

})
export class AppComponent implements OnInit {
  title = 'angular-file-upload-download';
  
  userName!: string;
  selectedFile!:File;

  userList: User[] = [];

  constructor(private httpClient: HttpClient) { }
  ngOnInit(): void {

    this.getUserList();

  }
  private getUserList() {
    this.httpClient.get<User[]>("http://localhost:8080/api/v1/file").subscribe(response => {
      this.userList = response;


    }, error => {
      console.log("error occured while fetching user list");
    });
  }

  onFileSelected(event:any){
    this.selectedFile=event.target.files[0];
  }
  save():void{
 
   const formData=new FormData();
   formData.append("name",this.userName);
   formData.append("file",this.selectedFile);
   
    this.httpClient.post("http://localhost:8080/api/v1/file",formData).subscribe(response=>{
      console.log(response);
      this.getUserList();
    },error=>{
      console.log(error);
    });
    console.log("saved");

  }

  downloadImage(base64Image: string, userName: string) {
    const link = document.createElement('a');
    link.href = 'data:image/png;base64,' + base64Image;
    link.download = `${userName}_image.png`;
    link.click();
  }

  deleteUser(userId: number) {
    this.httpClient.delete(`http://localhost:8080/api/v1/file/${userId}`).subscribe(response => {
      console.log(response);
      this.getUserList(); // Actualizar la lista de usuarios después de eliminar
    }, error => {
      console.log("error occurred while deleting user");
    });
  }





}
