import { IQuestion } from 'app/shared/model/question.model';

export interface IProfile {
  id?: number;
  name?: string;
  jobTitle?: string;
  notes?: string;
  me?: boolean;
  relation?: string;
  profileVariantName?: string;
  profileVariantId?: number;
  userLogin?: string;
  userId?: number;
  questions?: IQuestion[];
}

export class Profile implements IProfile {
  constructor(
    public id?: number,
    public name?: string,
    public jobTitle?: string,
    public notes?: string,
    public me?: boolean,
    public relation?: string,
    public profileVariantName?: string,
    public profileVariantId?: number,
    public userLogin?: string,
    public userId?: number,
    public questions?: IQuestion[]
  ) {
    this.me = this.me || false;
  }
}
