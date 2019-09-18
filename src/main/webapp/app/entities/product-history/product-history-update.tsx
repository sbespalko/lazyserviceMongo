import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { getEntity, updateEntity, createEntity, reset } from './product-history.reducer';
import { IProductHistory } from 'app/shared/model/product-history.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProductHistoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IProductHistoryUpdateState {
  isNew: boolean;
  productId: string;
}

export class ProductHistoryUpdate extends React.Component<IProductHistoryUpdateProps, IProductHistoryUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      productId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getProducts();
  }

  saveEntity = (event, errors, values) => {
    values.operationDt = convertDateTimeToServer(values.operationDt);

    if (errors.length === 0) {
      const { productHistoryEntity } = this.props;
      const entity = {
        ...productHistoryEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/product-history');
  };

  render() {
    const { productHistoryEntity, products, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="lazyserviceMongoApp.productHistory.home.createOrEditLabel">Create or edit a ProductHistory</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : productHistoryEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="product-history-id">ID</Label>
                    <AvInput id="product-history-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="operationDtLabel" for="product-history-operationDt">
                    Operation Dt
                  </Label>
                  <AvInput
                    id="product-history-operationDt"
                    type="datetime-local"
                    className="form-control"
                    name="operationDt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.productHistoryEntity.operationDt)}
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="opertationTypeLabel" for="product-history-opertationType">
                    Opertation Type
                  </Label>
                  <AvInput
                    id="product-history-opertationType"
                    type="select"
                    className="form-control"
                    name="opertationType"
                    value={(!isNew && productHistoryEntity.opertationType) || 'PUT'}
                  >
                    <option value="PUT">PUT</option>
                    <option value="TAKE">TAKE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="sumLabel" for="product-history-sum">
                    Sum
                  </Label>
                  <AvField id="product-history-sum" type="text" name="sum" />
                </AvGroup>
                <AvGroup>
                  <Label for="product-history-product">Product</Label>
                  <AvInput id="product-history-product" type="select" className="form-control" name="productId">
                    <option value="" key="0" />
                    {products
                      ? products.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/product-history" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  products: storeState.product.entities,
  productHistoryEntity: storeState.productHistory.entity,
  loading: storeState.productHistory.loading,
  updating: storeState.productHistory.updating,
  updateSuccess: storeState.productHistory.updateSuccess
});

const mapDispatchToProps = {
  getProducts,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProductHistoryUpdate);
