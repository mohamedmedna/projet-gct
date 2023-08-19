import { Servic } from './Servic';
export class Fichier {
  constructor(
    public iddoc: number,
    public name?: string,
    public docUrl?: string,
    public idService?: number,
    public form?: string
  ) {}
}
