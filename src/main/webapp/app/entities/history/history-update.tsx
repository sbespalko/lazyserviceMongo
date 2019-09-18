import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { getEntity, updateEntity, createEntity, reset } from './history.reducer';
import { IHistory } from 'app/shared/model/history.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IHistoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IHistoryUpdateState {
  isNew: boolean;
  clientId: string;
}

export class HistoryUpdate extends React.Component<IHistoryUpdateProps, IHistoryUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      clientId: '0',
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

    this.props.getClients();
  }

  saveEntity = (event, errors, values) => {
    values.startDt = convertDateTimeToServer(values.startDt);
    values.endDt = convertDateTimeToServer(values.endDt);

    if (errors.length === 0) {
      const { historyEntity } = this.props;
      const entity = {
        ...historyEntity,
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
    this.props.history.push('/entity/history');
  };

  render() {
    const { historyEntity, clients, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="lazyserviceMongoApp.history.home.createOrEditLabel">Create or edit a History</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : historyEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="history-id">ID</Label>
                    <AvInput id="history-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="startDtLabel" for="history-startDt">
                    Start Dt
                  </Label>
                  <AvInput
                    id="history-startDt"
                    type="datetime-local"
                    className="form-control"
                    name="startDt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.historyEntity.startDt)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="endDtLabel" for="history-endDt">
                    End Dt
                  </Label>
                  <AvInput
                    id="history-endDt"
                    type="datetime-local"
                    className="form-control"
                    name="endDt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.historyEntity.endDt)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="riskPowerLabel" for="history-riskPower">
                    Risk Power
                  </Label>
                  <AvField id="history-riskPower" type="string" className="form-control" name="riskPower" />
                </AvGroup>
                <AvGroup>
                  <Label for="history-client">Client</Label>
                  <AvInput id="history-client" type="select" className="form-control" name="clientId">
                    <option value="" key="0" />
                    {clients
                      ? clients.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/history" replace color="info">
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
  clients: storeState.client.entities,
  historyEntity: storeState.history.entity,
  loading: storeState.history.loading,
  updating: storeState.history.updating,
  updateSuccess: storeState.history.updateSuccess
});

const mapDispatchToProps = {
  getClients,
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
)(HistoryUpdate);
