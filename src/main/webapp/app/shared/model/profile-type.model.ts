export interface IProfileType {
  id?: number;
  name?: string;
  description?: string;
}

export class ProfileType implements IProfileType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
