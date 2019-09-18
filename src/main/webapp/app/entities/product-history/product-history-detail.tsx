import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './product-history.reducer';
import { IProductHistory } from 'app/shared/model/product-history.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductHistoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProductHistoryDetail extends React.Component<IProductHistoryDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { productHistoryEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            ProductHistory [<b>{productHistoryEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="operationDt">Operation Dt</span>
            </dt>
            <dd>
              <TextFormat value={productHistoryEntity.operationDt} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="opertationType">Opertation Type</span>
            </dt>
            <dd>{productHistoryEntity.opertationType}</dd>
            <dt>
              <span id="sum">Sum</span>
            </dt>
            <dd>{productHistoryEntity.sum}</dd>
            <dt>Product</dt>
            <dd>{productHistoryEntity.productId ? productHistoryEntity.productId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/product-history" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/product-history/${productHistoryEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ productHistory }: IRootState) => ({
  productHistoryEntity: productHistory.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProductHistoryDetail);
