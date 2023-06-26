import { Service } from "./Service";
export class Fichier {
  constructor(public iddoc?:number,public name?: string, public doc_url?: string,public service?:Service) {}
}
