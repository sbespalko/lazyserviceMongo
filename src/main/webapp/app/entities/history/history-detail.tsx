import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './history.reducer';
import { IHistory } from 'app/shared/model/history.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHistoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class HistoryDetail extends React.Component<IHistoryDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { historyEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            History [<b>{historyEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="startDt">Start Dt</span>
            </dt>
            <dd>
              <TextFormat value={historyEntity.startDt} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="endDt">End Dt</span>
            </dt>
            <dd>
              <TextFormat value={historyEntity.endDt} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="riskPower">Risk Power</span>
            </dt>
            <dd>{historyEntity.riskPower}</dd>
            <dt>Client</dt>
            <dd>{historyEntity.clientId ? historyEntity.clientId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/history" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/history/${historyEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ history }: IRootState) => ({
  historyEntity: history.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(HistoryDetail);
