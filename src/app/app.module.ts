import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { SupervisorDashComponent } from './supervisor-dash/supervisor-dash.component';
import { WorkerDashComponent } from './worker-dash/worker-dash.component';
import { AdddocumentComponent } from './adddocument/adddocument.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { UploadDocumentService } from './upload-document.service';
import { DocumentsUploadedComponent } from './documents-uploaded/documents-uploaded.component';
import { DocumentsbyMarketingComponent } from './documentsby-marketing/documentsby-marketing.component';
import { DocumentsbyLogisticsComponent } from './documentsby-logistics/documentsby-logistics.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SupervisorDashComponent,
    WorkerDashComponent,
    AdddocumentComponent,
    DocumentsUploadedComponent,
    DocumentsbyMarketingComponent,
    DocumentsbyLogisticsComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, FormsModule, HttpClientModule],
  providers: [UploadDocumentService],
  bootstrap: [AppComponent],
})
export class AppModule {}
