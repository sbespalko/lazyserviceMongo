import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Client from './client';
import History from './history';
import Address from './address';
import Product from './product';
import ProductHistory from './product-history';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/client`} component={Client} />
      <ErrorBoundaryRoute path={`${match.url}/history`} component={History} />
      <ErrorBoundaryRoute path={`${match.url}/address`} component={Address} />
      <ErrorBoundaryRoute path={`${match.url}/product`} component={Product} />
      <ErrorBoundaryRoute path={`${match.url}/product-history`} component={ProductHistory} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
