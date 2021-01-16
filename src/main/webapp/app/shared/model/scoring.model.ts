export interface IScoring {
  id?: number;
  score1?: number;
  score2?: number;
  profileVariantName?: string;
  profileVariantId?: number;
}

export class Scoring implements IScoring {
  constructor(
    public id?: number,
    public score1?: number,
    public score2?: number,
    public profileVariantName?: string,
    public profileVariantId?: number
  ) {}
}
