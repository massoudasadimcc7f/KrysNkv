export interface IProfileTypeInfo {
  id?: number;
  chapter?: string;
  rank?: number;
  h1?: string;
  h2?: string;
  content?: string;
  profileTypeName?: string;
  profileTypeId?: number;
  profileVariantName?: string;
  profileVariantId?: number;
}

export class ProfileTypeInfo implements IProfileTypeInfo {
  constructor(
    public id?: number,
    public chapter?: string,
    public rank?: number,
    public h1?: string,
    public h2?: string,
    public content?: string,
    public profileTypeName?: string,
    public profileTypeId?: number,
    public profileVariantName?: string,
    public profileVariantId?: number
  ) {}
}
