import { useEffect, useState } from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

function ViewBalanceModal(props) {
  const [show, setShow] = useState(props.show);

  const handleClose = () => props.setShow(false);

  return (
    <>
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          {/* <Modal.Title>Modal heading</Modal.Title> */}
        </Modal.Header>
        <Modal.Body>Your balance is ...</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" color="blue" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default ViewBalanceModal;
