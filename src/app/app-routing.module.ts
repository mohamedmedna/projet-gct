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
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './auth/auth.guard';
import { UserComponent } from './user/user.component';
import { UserListComponent } from './user-list/user-list.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'supervisordash', component: SupervisorDashComponent },
  {
    path: 'adddocument',
    component: AdddocumentComponent,
    canActivate: [AuthGuard],
    data: { roles: ['Admin'] },
  },
  { path: 'documentsuploaded', component: DocumentsUploadedComponent },
  { path: 'addservice', component: AddserviceComponent },
  { path: 'services-list', component: ServicesListComponent },
  { path: 'modifiers-champs/:id', component: ModifierChampsComponent },
  { path: 'formulaire', component: FormulaireComponent },
  {
    path: 'edit/:id/:iddoc/:name',
    component: EditDocumentComponent,
  },
  { path: 'addUser', component: UserComponent },
  { path: 'users-list', component: UserListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
