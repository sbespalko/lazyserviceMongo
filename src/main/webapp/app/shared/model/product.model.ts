import { Moment } from 'moment';
import { IProductHistory } from 'app/shared/model/product-history.model';

export interface IProduct {
  id?: string;
  code?: string;
  name?: string;
  startDt?: Moment;
  endDt?: Moment;
  clientId?: string;
  histories?: IProductHistory[];
}

export const defaultValue: Readonly<IProduct> = {};
