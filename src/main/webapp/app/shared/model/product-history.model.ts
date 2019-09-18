import { Moment } from 'moment';
import { OperationType } from 'app/shared/model/enumerations/operation-type.model';

export interface IProductHistory {
  id?: string;
  operationDt?: Moment;
  opertationType?: OperationType;
  sum?: number;
  productId?: string;
}

export const defaultValue: Readonly<IProductHistory> = {};
