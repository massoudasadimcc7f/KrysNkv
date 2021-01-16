export interface IProfileVariant {
  id?: number;
  name?: string;
  color?: string;
  profileTypeName?: string;
  profileTypeId?: number;
}

export class ProfileVariant implements IProfileVariant {
  constructor(
    public id?: number,
    public name?: string,
    public color?: string,
    public profileTypeName?: string,
    public profileTypeId?: number
  ) {}
}
