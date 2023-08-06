import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SupervisorDashComponent } from './supervisor-dash/supervisor-dash.component';
import { AdddocumentComponent } from './adddocument/adddocument.component';
import { DocumentsUploadedComponent } from './documents-uploaded/documents-uploaded.component';
import { AddserviceComponent } from './addservice/addservice.component';
import { ServicesListComponent } from './services-list/services-list.component';
import { FormulaireComponent } from './formulaire/formulaire.component';
import { ModifierChampsComponent } from './modifier-champs/modifier-champs.component';
import { EditDocumentComponent } from './edit-document/edit-document.component';

const routes: Routes = [
  {
    path: '',
    component: SupervisorDashComponent,
  },
  { path: 'supervisordash', component: SupervisorDashComponent },
  { path: 'adddocument', component: AdddocumentComponent },
  { path: 'documentsuploaded', component: DocumentsUploadedComponent },
  { path: 'addservice', component: AddserviceComponent },
  { path: 'services-list', component: ServicesListComponent },
  { path: 'modifiers-champs/:id', component: ModifierChampsComponent },
  { path: 'formulaire', component: FormulaireComponent },
  { path: 'edit/:id/:iddoc/:name', component: EditDocumentComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
