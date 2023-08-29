import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
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
import { DocumentsByServiceNameComponent } from './documents-by-service-name/documents-by-service-name.component';
import { GeneratedDocumentComponent } from './generated-document/generated-document.component';
import { GeneratedDocumentByServiceNameComponent } from './generated-document-by-service-name/generated-document-by-service-name.component';
import { FormulaireListComponent } from './formulaire-list/formulaire-list.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AddRoleComponent } from './add-role/add-role.component';
import { RolesListComponent } from './roles-list/roles-list.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { UpdateUserComponent } from './update-user/update-user.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: LoginComponent },

  {
    path: 'adddocument',
    component: AdddocumentComponent,
    canActivate: [AuthGuard],
    data: { roles: ['Supervisor'] },
  },
  { path: 'documentsuploaded', component: DocumentsUploadedComponent },
  { path: 'documentsmodified', component: GeneratedDocumentComponent },
  {
    path: 'addservice',
    component: AddserviceComponent,
    canActivate: [AuthGuard],
    data: { roles: ['Admin'] },
  },
  {
    path: 'addrole',
    component: AddRoleComponent,
    canActivate: [AuthGuard],
    data: { roles: ['Admin'] },
  },
  {
    path: 'services-list',
    component: ServicesListComponent,
    canActivate: [AuthGuard],
    data: { roles: ['Admin'] },
  },
  {
    path: 'roles-list',
    component: RolesListComponent,
    canActivate: [AuthGuard],
    data: { roles: ['Admin'] },
  },
  {
    path: 'modifiers-champs/:id',
    component: ModifierChampsComponent,
    canActivate: [AuthGuard],
    data: { roles: ['Supervisor'] },
  },
  {
    path: 'addformulaire',
    component: FormulaireComponent,
    canActivate: [AuthGuard],
    data: { roles: ['Supervisor'] },
  },
  {
    path: 'formulaire-list',
    component: FormulaireListComponent,
    canActivate: [AuthGuard],
    data: { roles: ['Supervisor'] },
  },
  {
    path: 'edit/:id/:iddoc/:name',
    component: EditDocumentComponent,
    canActivate: [AuthGuard],
    data: { roles: ['Supervisor'] },
  },
  {
    path: 'addUser',
    component: UserComponent,
    canActivate: [AuthGuard],
    data: { roles: ['Admin'] },
  },
  {
    path: 'users-list',
    component: UserListComponent,
    canActivate: [AuthGuard],
    data: { roles: ['Admin'] },
  },

  {
    path: 'updateUser/:id',
    component: UpdateUserComponent,
    canActivate: [AuthGuard],
    data: { roles: ['Admin'] },
  },
  {
    path: 'loadDocumentsByServiceName/:serviceName',
    component: DocumentsByServiceNameComponent,
  },
  {
    path: 'loadgeneratedDocumentsByServiceName/:serviceName',
    component: GeneratedDocumentByServiceNameComponent,
  },
  { path: 'navbar', component: NavbarComponent },
  { path: 'forbidden', component: ForbiddenComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
