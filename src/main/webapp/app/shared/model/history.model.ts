import { Moment } from 'moment';

export interface IHistory {
  id?: string;
  startDt?: Moment;
  endDt?: Moment;
  riskPower?: number;
  clientId?: string;
}

export const defaultValue: Readonly<IHistory> = {};
