import { IProduct } from 'app/shared/model/product.model';
import { IHistory } from 'app/shared/model/history.model';

export interface IClient {
  id?: string;
  name?: string;
  products?: IProduct[];
  histories?: IHistory[];
  addressId?: string;
}

export const defaultValue: Readonly<IClient> = {};
