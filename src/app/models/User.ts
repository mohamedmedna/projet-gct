export class User {
  constructor(
    public id?: number,
    public userName?: string,
    public nomPrenom?: string,
    public userPassword?: string,
    public nomService?: string,
    public roleNames?: string[]
  ) {}
}
