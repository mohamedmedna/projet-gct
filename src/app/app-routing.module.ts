import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { SupervisorDashComponent } from './supervisor-dash/supervisor-dash.component';
import { WorkerDashComponent } from './worker-dash/worker-dash.component';
import { AdddocumentComponent } from './adddocument/adddocument.component';
import { DocumentsUploadedComponent } from './documents-uploaded/documents-uploaded.component';
import { Form1Component } from './form1/form1.component';
import { Form2Component } from './form2/form2.component';
import { Form3Component } from './form3/form3.component';
import { AddserviceComponent } from './addservice/addservice.component';
import { ServicesListComponent } from './services-list/services-list.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  {
    path: '',
    component: HomeComponent,
  },
  { path: 'supervisordash', component: SupervisorDashComponent },
  { path: 'workerdash', component: WorkerDashComponent },
  { path: 'adddocument', component: AdddocumentComponent },
  { path: 'documentsuploaded', component: DocumentsUploadedComponent },
  { path: 'addservice', component: AddserviceComponent },
  { path: 'services-list', component: ServicesListComponent },

  { path: 'form1', component: Form1Component },
  { path: 'form2', component: Form2Component },
  { path: 'form3', component: Form3Component },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
