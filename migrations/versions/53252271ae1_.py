"""empty message

Revision ID: 53252271ae1
Revises: 1bf3e6e7226e
Create Date: 2015-11-07 19:13:01.127852

"""

# revision identifiers, used by Alembic.
revision = '53252271ae1'
down_revision = '1bf3e6e7226e'

from alembic import op
import sqlalchemy as sa


def upgrade():
    ### commands auto generated by Alembic - please adjust! ###
    op.create_table('interests',
    sa.Column('id', sa.Integer(), nullable=False),
    sa.Column('name', sa.String(length=80), nullable=True),
    sa.Column('user_id', sa.Integer(), nullable=True),
    sa.ForeignKeyConstraint(['user_id'], ['users.id'], ),
    sa.PrimaryKeyConstraint('id')
    )
    op.add_column(u'projects', sa.Column('address', sa.String(length=120), nullable=True))
    op.add_column(u'projects', sa.Column('description', sa.Text(), nullable=True))
    op.add_column(u'projects', sa.Column('image', sa.Text(), nullable=True))
    op.add_column(u'projects', sa.Column('num_people', sa.Integer(), nullable=True))
    op.drop_column(u'projects', 'img')
    op.add_column(u'users', sa.Column('address', sa.String(length=120), nullable=True))
    op.add_column(u'users', sa.Column('phone', sa.String(length=20), nullable=True))
    ### end Alembic commands ###


def downgrade():
    ### commands auto generated by Alembic - please adjust! ###
    op.drop_column(u'users', 'phone')
    op.drop_column(u'users', 'address')
    op.add_column(u'projects', sa.Column('img', sa.TEXT(), autoincrement=False, nullable=True))
    op.drop_column(u'projects', 'num_people')
    op.drop_column(u'projects', 'image')
    op.drop_column(u'projects', 'description')
    op.drop_column(u'projects', 'address')
    op.drop_table('interests')
    ### end Alembic commands ###
