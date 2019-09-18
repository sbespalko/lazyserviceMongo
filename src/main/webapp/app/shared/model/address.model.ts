export interface IAddress {
  id?: string;
  city?: string;
  street?: string;
  house?: string;
}

export const defaultValue: Readonly<IAddress> = {};
