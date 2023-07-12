import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { SupervisorDashComponent } from './supervisor-dash/supervisor-dash.component';
import { WorkerDashComponent } from './worker-dash/worker-dash.component';
import { AdddocumentComponent } from './adddocument/adddocument.component';
import { DocumentsUploadedComponent } from './documents-uploaded/documents-uploaded.component';
import { AddserviceComponent } from './addservice/addservice.component';
import { ServicesListComponent } from './services-list/services-list.component';
import { FormulaireComponent } from './formulaire/formulaire.component';

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

  { path: 'formulaire', component: FormulaireComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
