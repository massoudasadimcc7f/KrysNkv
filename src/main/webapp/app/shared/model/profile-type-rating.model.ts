export interface IProfileTypeRating {
  id?: number;
  characteristic?: string;
  rating?: number;
  profileTypeName?: string;
  profileTypeId?: number;
}

export class ProfileTypeRating implements IProfileTypeRating {
  constructor(
    public id?: number,
    public characteristic?: string,
    public rating?: number,
    public profileTypeName?: string,
    public profileTypeId?: number
  ) {}
}
