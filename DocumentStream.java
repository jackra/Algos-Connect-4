/**
 * @author Deepak Rohan Sekar, Ganesh Ramamoorthy
 * @version 1.0
 * @date March 21, 2014 
 */
import java.io.OutputStream;
import java.io.IOException;
import javax.swing.text.*;

/**
 * An OutputStream implementation that places it's output in a swing text model
 * (Document). The Document can be either a plain text or styled document
 * implementation. If styled, the attributes assigned to the output stream will
 * be used in the display of the output.
 */
public class DocumentStream extends OutputStream {

	/**
	 * Constructs an output stream that will output to the given document with
	 * the given set of character attributes.
	 *
	 * @param doc
	 *            the document to write to.
	 * @param a
	 *            the character attributes to use for the written text.
	 */
	public DocumentStream(Document doc, AttributeSet a) {
		this.doc = doc;
		this.a = a;
	}

	/**
	 * Constructs an output stream that will output to the given document with
	 * whatever the default attributes are.
	 *
	 * @param doc
	 *            the document to write to.
	 */
	public DocumentStream(Document doc) {
		this(doc, null);
	}

	/**
	 * Writes the specified byte to this output stream.
	 * <p>
	 * Subclasses of <code>OutputStream</code> must provide an implementation
	 * for this method.
	 *
	 * @param b
	 *            the <code>byte</code>.
	 * @exception IOException
	 *                if an I/O error occurs.
	 * @since JDK1.0
	 */
	public void write(int b) throws IOException {
		one[0] = (byte) b;
		write(one, 0, 1);
	}

	/**
	 * Writes <code>len</code> bytes from the specified byte array starting at
	 * offset <code>off</code> to this output stream.
	 * <p>
	 * The <code>write</code> method of <code>OutputStream</code> calls the
	 * write method of one argument on each of the bytes to be written out.
	 * Subclasses are encouraged to override this method and provide a more
	 * efficient implementation.
	 *
	 * @param b
	 *            the data.
	 * @param off
	 *            the start offset in the data.
	 * @param len
	 *            the number of bytes to write.
	 * @exception IOException
	 *                if an I/O error occurs.
	 * @since JDK1.0
	 */
	public void write(byte b[], int off, int len) throws IOException {
		try {
			doc.insertString(doc.getLength(), new String(b, off, len), a);
		} catch (BadLocationException ble) {
			throw new IOException(ble.getMessage());
		}
	}

	private byte[] one = new byte[1];
	private Document doc;
	private AttributeSet a;
}
