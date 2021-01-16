import { IProfile } from 'app/shared/model/profile.model';

export interface IQuestion {
  id?: number;
  questionSet?: number;
  question?: number;
  answer?: string;
  description?: string;
  tag?: string;
  score?: number;
  profiles?: IProfile[];
}

export class Question implements IQuestion {
  constructor(
    public id?: number,
    public questionSet?: number,
    public question?: number,
    public answer?: string,
    public description?: string,
    public tag?: string,
    public score?: number,
    public profiles?: IProfile[]
  ) {}
}
