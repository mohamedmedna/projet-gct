import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SupervisorDashComponent } from './supervisor-dash/supervisor-dash.component';
import { AdddocumentComponent } from './adddocument/adddocument.component';
import { FormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { UploadDocumentService } from './upload-document.service';
import { DocumentsUploadedComponent } from './documents-uploaded/documents-uploaded.component';

import { AddserviceComponent } from './addservice/addservice.component';
import { ServicesListComponent } from './services-list/services-list.component';
import { FormulaireComponent } from './formulaire/formulaire.component';
import { ModifierChampsComponent } from './modifier-champs/modifier-champs.component';
import { EditDocumentComponent } from './edit-document/edit-document.component';
import { LoginComponent } from './login/login.component';
import { RouterModule } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';
import { AuthInterceptor } from './auth/auth.interceptor';
import { UserService } from './authServices/user.service';
import { AddServiceService } from './add-service.service';
import { FormulaireService } from 'src/formulaire.service';
import { UserComponent } from './user/user.component';
import { UserListComponent } from './user-list/user-list.component';

@NgModule({
  declarations: [
    AppComponent,
    SupervisorDashComponent,
    AdddocumentComponent,
    DocumentsUploadedComponent,
    AddserviceComponent,
    ServicesListComponent,
    FormulaireComponent,
    ModifierChampsComponent,
    EditDocumentComponent,
    LoginComponent,
    UserComponent,
    UserListComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RouterModule,
  ],
  providers: [
    UploadDocumentService,
    AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
    UserService,
    AddServiceService,
    FormulaireService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
