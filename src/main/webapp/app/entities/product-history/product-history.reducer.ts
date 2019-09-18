import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProductHistory, defaultValue } from 'app/shared/model/product-history.model';

export const ACTION_TYPES = {
  FETCH_PRODUCTHISTORY_LIST: 'productHistory/FETCH_PRODUCTHISTORY_LIST',
  FETCH_PRODUCTHISTORY: 'productHistory/FETCH_PRODUCTHISTORY',
  CREATE_PRODUCTHISTORY: 'productHistory/CREATE_PRODUCTHISTORY',
  UPDATE_PRODUCTHISTORY: 'productHistory/UPDATE_PRODUCTHISTORY',
  DELETE_PRODUCTHISTORY: 'productHistory/DELETE_PRODUCTHISTORY',
  RESET: 'productHistory/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProductHistory>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ProductHistoryState = Readonly<typeof initialState>;

// Reducer

export default (state: ProductHistoryState = initialState, action): ProductHistoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTHISTORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PRODUCTHISTORY):
    case REQUEST(ACTION_TYPES.UPDATE_PRODUCTHISTORY):
    case REQUEST(ACTION_TYPES.DELETE_PRODUCTHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTHISTORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTHISTORY):
    case FAILURE(ACTION_TYPES.CREATE_PRODUCTHISTORY):
    case FAILURE(ACTION_TYPES.UPDATE_PRODUCTHISTORY):
    case FAILURE(ACTION_TYPES.DELETE_PRODUCTHISTORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTHISTORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTHISTORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRODUCTHISTORY):
    case SUCCESS(ACTION_TYPES.UPDATE_PRODUCTHISTORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRODUCTHISTORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/product-histories';

// Actions

export const getEntities: ICrudGetAllAction<IProductHistory> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTHISTORY_LIST,
    payload: axios.get<IProductHistory>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IProductHistory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTHISTORY,
    payload: axios.get<IProductHistory>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IProductHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRODUCTHISTORY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProductHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRODUCTHISTORY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProductHistory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRODUCTHISTORY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
