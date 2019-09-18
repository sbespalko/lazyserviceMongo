import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductHistory from './product-history';
import ProductHistoryDetail from './product-history-detail';
import ProductHistoryUpdate from './product-history-update';
import ProductHistoryDeleteDialog from './product-history-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductHistoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductHistory} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ProductHistoryDeleteDialog} />
  </>
);

export default Routes;
